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
    P_ASSERT( map != NULL /*&& solution != NULL*/, "empty arguments" );

    pNeighborhood nb;
    pMove move;
    pSolution s_sol, * best;

    s_sol.init_random( map );
    best = &s_sol;

    move.init();

    pOut->print( ">>> starting tabu-search loop...\n" );

    while( move.remaining() )
    {
        pOut->print( ">>> creating neighborhood... %p, %p ", best, &move );
        if( nb.create( best, &move ) == 0 )
        {
            pOut->print( "[ %sOK%s ]\n", COL_GRN, COL_DEF );
        }
        else
        {
            pOut->print( "[ %s!!%s ]\n", COL_RED, COL_DEF );
        }

        pOut->print( ">>> looking for best solution... " );
        best = nb.best_result( map );
        pOut->print( "(%2.2f) [ %sOK%s ]\n", best->penalty( map ), COL_GRN, COL_DEF );

        if( on_tabu( best ) )
        {
            pOut->print( ">>> tabu list element repeat\n" );
        }
        else
        {
            pOut->print( ">>> adding to tabu list %p\n", best );
            tabu_list.push_back( best );
        }
        move.next();

        pOut->print( "[%d]: ", best->list.size() );
        for( size_t i=0; i<best->list.size(); ++i )
        {
            pOut->print( "%d; ", best->list[i]->get_type() );
        }
        pOut->print( "\n" );
    }

    return( 0 );
}

