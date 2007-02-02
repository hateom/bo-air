#include "p_tabu_search.h"
#include "p_error.h"
#include "p_neighborhood.h"
#include "p_move.h"
#include "p_output.h"

pTabuSearch::pTabuSearch()
{
}

pTabuSearch::~pTabuSearch()
{
}

bool pTabuSearch::on_tabu( pSolution * s )
{
    P_ASSERT( s != NULL, "empty argument" );
    /// poprawic na std::find!!!
    for( ptsIter i=tabu_list.begin(); i<tabu_list.end(); ++i )
    {
        if( s->equals( *i ) )
        {
            return( true );
        }
    }

    return( false );
}

int pTabuSearch::find_solution( pMap * map, pSolution * solution )
{
    P_ASSERT( map != NULL, "empty argument <pMap * map>" );

    pNeighborhood nb;
    pMove move;
    pSolution s_sol, * best;//, * last;
    int n_null, nl;

    s_sol.init_random( map );
    best = &s_sol;

    move.init();

    pOut->print( "%s>>>%s starting tabu-search loop...\n", COL_GRN, COL_GRY );

    while( move.remaining() )
    {
        if( nb.create( best, &move ) != 0 )
        {
            pOut->print( "[ %s!!%s ]\n", COL_RED, COL_DEF );
        }

        pOut->print( ">>> looking for best solution... " );
//		last = best;
        best = nb.best_result( map );
		if( !best )
		{
			pOut->print( ">>> empty neighborhood\n" );
//			best = last;
			continue;
		}

        if( on_tabu( best ) )
        {
            pOut->print( "\n%s>>  %stabu list element repeat\n", COL_BLU, COL_GRY );
            pOut->print( "%s>>  %sommiting tabu list result\n", COL_BLU, COL_GRY );
            move.next();
            continue;
        }
        else
        {
            pOut->print( "adding to tabu list <cost:%2.2f> ", best->penalty( map ) );
            tabu_list.push_back( best );
            pOut->print( "[ %sOK%s ]\n", COL_GRN, COL_DEF );
        }
        move.next();

        n_null = best->not_null();
        nl = 0;
        if( !n_null )
        {
            pOut->print( "%s>>> ommiting empty result.\n", COL_GRY );
            continue;
        }
        
        pOut->printa( "%2.2f;", best->penalty( map ) );
        for( size_t i=0; i<best->list.size(); ++i )
        {
            if( best->list[i]->get_type() == 0 ) continue;
            pOut->printa( "%d:%d%c", i, best->list[i]->get_type(), nl < (n_null-1) ? ';':'\0' );
            nl++;
        }
        pOut->printa( ";\n" );
    }

    pSolution * b;
    b = tabu_best( map );
    if( b )
    {
        pOut->print( ">>> best solution: %2.2f\n", b->penalty( map ) );
    }

    return( 0 );
}

pSolution * pTabuSearch::tabu_best( pMap * map )
{
    P_ASSERT( map != NULL, "empty argument <pMap * map>" );

    size_t size;
    size = tabu_list.size();
    pSolution * best;

    if( !size ) return( NULL );

    best = tabu_list[0];
    if( size == 1 ) return( best );

    for( size_t i=1; i<size; ++i )
    {
        if( tabu_list[i]->penalty( map ) < best->penalty( map ) )
        {
            best = tabu_list[i];
        }
    }

    return( best );
}
