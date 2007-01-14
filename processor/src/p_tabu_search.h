#ifndef __P_TABU_SEARCH_H__
#define __P_TABU_SEARCH_H__

#include <vector>
#include "p_map.h"
#include "p_solution.h"

class pTabuSearch
{
    typedef std::vector<pSolution*>::iterator ptsIter;
public:
    pTabuSearch();
    virtual ~pTabuSearch();

    int find_solution( pMap * map, pSolution * solution );
    bool on_tabu( pSolution * s );
    pSolution * tabu_best( pMap * map );

protected:
    std::vector<pSolution*> tabu_list;
};

#endif // __P_TABU_SEARCH_H__

