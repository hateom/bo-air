#include "p_tabu_search.h"
#include "p_error.h"

pTabuSearch::pTabuSearch()
{
}

pTabuSearch::~pTabuSearch()
{
}

int pTabuSearch::find_solution( pMap * map, pSolution * solution )
{
    P_ASSERT( map != NULL && solution != NULL, "empty arguments" );
    return( 0 );
}
