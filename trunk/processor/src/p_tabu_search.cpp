#include "p_tabu_search.h"
#include "p_error.h"
#include "p_neighborhood.h"
#include "p_output.h"
#include "p_map.h"
#include "p_cost_mgr.h"
#include "p_cfg_mgr.h"

#define CFG_GET( PARAM, VAR, DEF ) if( !pCfgMgr::get_int( PARAM, &VAR )) { VAR = DEF; }

#define def_K 100
#define def_T 5
#define def_ALPHA 2

pTabuSearch::pTabuSearch() : tabu_list(NULL), ssize(0)
{
}

pTabuSearch::~pTabuSearch()
{
    free();
}

int pTabuSearch::exec()
{
    ssize = Map->solution_size();
    create_tl( ssize );

    pSolution s_a( ssize ), s_min( ssize ), s_temp( ssize ), s_temp2( ssize ), s_p( ssize ), s_pp( ssize );
    float Q_min, q_min, q_min2, q_temp, q_temp2;
    bool ch = false;
    size_t ip = 0, jp = 1, ipp = 0, jpp = 1;

    CFG_GET( "K",     p_K,     def_K );
    CFG_GET( "T",     p_T,     def_T );
    CFG_GET( "APLHA", p_ALPHA, def_ALPHA );

    s_a.init( pc::transmitter_type_count() );

    s_min = s_a;
    Q_min = Map->eval( &s_a );
    q_min = q_min2 = Q_min;
    s_p = s_min;
    s_pp = s_min;

    for( size_t z=0; z<3; ++z )
    {
        for( size_t k=0; k<p_K; ++k )
        {
            for( size_t j=0; j<ssize; ++j )
            {
                for( size_t i=j; i<ssize; ++i )
                {
                    if( short_list( i, j ) == 0 )
                    {
                        // PI( i*, j* )

                        s_temp = s_a;
                        if( k % 3 == 0 )
                        {
                            s_temp.inc( i, j );
                        }
                        else if( k % 4 == 0 )
                        {
                            s_temp.dec( i, j );
                        }                        
                        else
                        {
                            s_temp.swap( i, j );
                        }
                        q_temp = Map->eval( &s_temp );
                        q_temp += (float)( (p_ALPHA*long_list( i, j ))/(k+1) );

//                        pOut->print( "// PI( i*, j* ) %2.2f - %2.2f //\n", q_temp, q_min );
    
                        if( q_temp < q_min ) 
                        {
                            q_min = q_temp;
                            ip = i;
                            jp = j;
                            s_p = s_temp;
                            pOut->print( "// better PI( i*, j* ) //\n" );
                        }
                    }
                    else if( short_list( i, j ) > 0 )
                    {
                        // PI( i', j' )

                        s_temp2 = s_a;
                        if( k % 9 == 0 )
                        {
                            s_temp2.inc( i, j );
                        }
                        else if( k % 7 == 0 )
                        {
                            s_temp2.dec( i, j );
                        }                        
                        else
                        {
                            s_temp2.swap( i, j );
                        }
                        
                        q_temp2 = Map->eval( &s_temp2 );

//                        pOut->print( "// PI( i', j' ) %2.2f - %2.2f //\n", q_temp2, q_min2 );

                        if( q_temp2 < q_min2 )
                        {
                            q_min2 = q_temp2;
                            ipp = i;
                            jpp = j;
                            s_pp = s_temp2;
                            pOut->print( "// better PI( i', j' ) //\n" );
                        }
                    }
                }
            }

            s_a = s_p;
            if( Map->eval( &s_a ) < Q_min )
            {
                Q_min = Map->eval( &s_a );
                s_min = s_a;
            }

            // aspiration

            if( q_min2 < Q_min )
            {
                s_a = s_pp;
                s_min = s_pp;
                Q_min = q_min2;
                ch = true;
                pOut->print( "// aspiration //\n" );
            }

            // memory correction

            decrease_short_list();
            if( ch == false )
            {
                short_list( ip, jp ) = p_T;
                long_list( ip, jp ) += 1;
            }
            else
            {
                short_list( ipp, jpp ) = p_T;
                long_list( ipp, jpp ) += 1;
            }
    
            pOut->print( "z(%d):k=%003d>> Q_min: %2.2f { ", z, k, Q_min );
            for( size_t s=0; s<ssize; ++s )
            {
                pOut->print( "%d ", s_min.vec[s] );
            }
            pOut->print( "}\n" );
        }
    }

    return 0;
}

void pTabuSearch::create_tl( size_t siz )
{
    tabu_list = new int[siz*siz];
    memset( tabu_list, 0, sizeof(int)*siz*siz );
}

void pTabuSearch::free()
{
    if( ssize != 0 )
    {
        delete [] tabu_list;
        tabu_list = NULL;
        ssize = 0;
    }
}

void pTabuSearch::decrease_short_list()
{
    for( size_t j=0; j<ssize; ++j )
    {
        for( size_t i=j; i<ssize; ++i )
        {
            if( short_list( i, j ) > 0 )
            {
                short_list( i, j ) -= 1;
            }
        }
    }
}

int & pTabuSearch::short_list( size_t i, size_t j )
{
    P_ASSERT( i < ssize || j < ssize || j == i, "out of range" );

    if( i > j )
    {
        return tabu_list[i+j*ssize];
    }
    else
    {
        return tabu_list[j+i*ssize];
    }
}

int & pTabuSearch::long_list( size_t i, size_t j )
{
    P_ASSERT( i < ssize || j < ssize || i == j, "out of range" );

    if( i < j )
    {
        return tabu_list[i+j*ssize];
    }
    else
    {
        return tabu_list[j+i*ssize];
    }
}
