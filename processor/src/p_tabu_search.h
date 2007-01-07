#ifndef __P_TABU_SEARCH_H__
#define __P_TABU_SEARCH_H__

#include "p_map.h"
#include "p_solution.h"

class pTabuSearch
{
public:
    pTabuSearch();
    virtual ~pTabuSearch();

    int find_solution( pMap * map, pSolution * solution );
};

#endif // __P_TABU_SEARCH_H__

