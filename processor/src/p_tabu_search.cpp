#include "p_tabu_search.h"
#include "p_error.h"
#include "p_neighborhood.h"
#include "p_output.h"
#include "p_map.h"
#include "p_cost_mgr.h"
#include "p_cfg_mgr.h"

#define PERM 6

#define CFG_GET( PARAM, VAR, DEF ) if( !pCfgMgr::get_int( PARAM, &VAR )) { VAR = DEF; }

#define def_K       35
#define def_T        5
#define def_ALPHA    2

#define PRINT_SOL( SOL, Q )                                             \
            pOut->printa( "%3.3f;", Q!=0.0f?-Q:0.0f );                  \
            for( size_t s=0; s<ssize; ++s )                             \
            {                                                           \
                if( SOL.vec[s] != 0 )                                   \
                {                                                       \
                    pOut->printa( "%d:%d;", s, SOL.vec[s] );            \
                }                                                       \
            }                                                           \
            pOut->printa( "\n" );

pTabuSearch::pTabuSearch() : tabu_list(NULL), ssize(0)
{
}

pTabuSearch::~pTabuSearch()
{
    free();
}

int pTabuSearch::init()
{
    ssize = Map->solution_size();
    pOut->print( ">> allocating tabu-list... [%dx%d] ", ssize, ssize );
    create_tl( ssize );
    pOut->print( "OK\n" );

    CFG_GET( "K",     p_K,     def_K );
    CFG_GET( "T",     p_T,     def_T );
    CFG_GET( "APLHA", p_ALPHA, def_ALPHA );

    return 0;
}


float pTabuSearch::improve_sol( pSolution * in, size_t i, size_t j, size_t ink, bool f )
{
    float q_temp;
    
    if( ink % PERM == 0 )
    {
        pOut->print( "imp: swap & inc %d, %d: ", i, j );
        in->swap( i, j );
        in->inc( i, j );
    }
    else if( ink % PERM == 1 ) //~~~
    {
        pOut->print( "imp: swap & dec %d, %d: ", i, j );
        in->swap( i, j );
        in->dec( i, j );
    }
    else if ( ink % PERM == 2 )
    {
        pOut->print( "imp: dec %d, %d: ", i, j );
        in->dec( i, j );
    }
    // /~~~
    else if ( ink % PERM == 3 )
    {
        pOut->print( "imp: inc %d, %d: ", i, j );
        in->inc( i, j );
    }
    //~~~
    else if( ink % PERM == 4 )
    {
        pOut->print( "imp: decinc %d, %d: ", i, j );
        in->decinc( i, j );
    }
    else if( ink % PERM == 5 )
    {
        pOut->print( "imp: incdec %d, %d: ", i, j );
        in->incdec( i, j );
    }
    // /~~~
    else
    {
        pOut->print( "imp: swap %d, %d: ", i, j );
        in->swap( i, j );
    }
    
    q_temp = Map->eval( in );
    
    if( ink > 0 && f )
    {
        q_temp += (float)( (p_ALPHA*long_list( i, j ))/ink );
    }

    pOut->print( "%2.2f\n", q_temp != 0.0f ? -q_temp : 0.0f );

    return q_temp;
}

int pTabuSearch::exec()
{
    pSolution s_a( ssize ), s_min( ssize ), s_temp( ssize ), s_temp2( ssize ), 
              s_p( ssize ), s_pp( ssize ), s_prev( ssize ), s_start( ssize );

    float Q_min, q_min, q_min2, qt;
    bool ch = false;
    size_t ip = 0, jp = 1, ipp = 0, jpp = 1;
    size_t p_K2 = p_K/2, r_s = 0, p_RS = 10;

    s_a.init( pc::transmitter_type_count(), Map->building_count() );

    // initialization
    s_min = s_a; s_prev = s_min; s_start = s_a;

    Q_min = Map->eval( &s_a ); q_min = q_min2 = Q_min;
    s_p = s_min; s_pp = s_min;
    pOut->print( ">> starting solution:\n" );
    PRINT_SOL( s_a, Q_min );

    int z = 0;
    size_t k = 0;

        while( true )
        {
            ++z;
//            s_temp = s_a;
            for( size_t j=0; j<ssize; ++j )
            {
                for( size_t i=j; i<ssize; ++i )
                {
                    if( i == j ) continue;
                    s_temp = s_a;
                    if( short_list( i, j ) == 0 )
                    {
                        // PI( i*, j* )
                        if( (qt = improve_sol( &s_temp, i, j, z )) < q_min )
                        {
                            pOut->print( "improve i*, j* [k:%d=%2.2f]\n", k, qt==0.0f?0.0f:-qt );
                            q_min = qt;
                            ip = i;
                            jp = j;
                            s_p = s_temp;
                        }
                    }
                    else if( short_list( i, j ) > 0 )
                    {
                        // PI( i', j' )
                        if( (qt = improve_sol( &s_temp, i, j, z, false )) < q_min2 )
                        {
                            pOut->print( "improve i', j' [k:%d=%2.2f]\n", k, qt==0.0f?0.0f:-qt );
                            q_min2 = qt;
                            ipp = i;
                            jpp = j;
                            s_pp = s_temp;
                        }
                    }
                }
            }

            s_a = s_p;
            if( q_min < Q_min )
            {
                Q_min = q_min;
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
            else ch = false;

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
    
            if( s_min == s_prev )
            {
                k++;
                if( k >= (size_t)p_K )
                {
                    break;
                }
                if( k > p_K2 && s_a == s_start && r_s < p_RS )
                {
                    pOut->print( ">> initializing solution (no improvement)\n" );
                    s_a.init( pc::transmitter_type_count(), Map->building_count() );
                    Q_min = Map->eval( &s_a ); q_min = q_min2 = Q_min;
                    s_start = s_a;
                    k = 0;
                    r_s++;
                }
            }
            else
            {
                k = 0;
            }
            s_prev = s_min;


            // print solution to output _ALWAYS_
            PRINT_SOL( s_min, Q_min );

        }

    // print short & long list
    pOut->print( "* long & short list:\n" );
    for( size_t a=0; a<ssize; ++a )
    {
        for( size_t b=0; b<ssize; ++b )
        {
            if( a == b )
            {
                pOut->print( "xx " );
            }
            else
            {
                pOut->print( "%02d ", tabu_list[b+a*ssize] );
            }
        }
        pOut->print( "\n" );
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
            if( i == j ) continue;
            if( short_list( i, j ) > 0 )
            {
                short_list( i, j ) -= 1;
            }
        }
    }
}

int & pTabuSearch::short_list( size_t i, size_t j )
{
    P_ASSERT( i < ssize && j < ssize && j != i, "out of range" );

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
    P_ASSERT( i < ssize && j < ssize && i != j, "out of range" );

    if( i < j )
    {
        return tabu_list[i+j*ssize];
    }
    else
    {
        return tabu_list[j+i*ssize];
    }
}

