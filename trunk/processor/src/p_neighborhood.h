#ifndef __P_NEIGHBORHOOD_H__
#define __P_NEIGHBORHOOD_H__

#include <vector>
#include "p_solution.h"
#include "p_move.h"
#include "p_map.h"

class pNeighborhood
{
    typedef std::vector<pSolution*>::iterator pnIter;
public:
    pNeighborhood();
    ~pNeighborhood();

    int create( pSolution * solution, pMove * move );
    pSolution * best_result( pMap * map );
    void release();

protected:
    int do_perm( pSolution * sol, int value );
    int do_switch( pSolution * sol );

    std::vector<pSolution*> list;
    int perm_val;
};

#endif // __P_NEIGHBORHOOD_H__

