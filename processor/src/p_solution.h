#ifndef __P_SOLUTION_H__
#define __P_SOLUTION_H__

#include <vector>
#include "p_transmitter.h"
#include "p_map.h"

/// solution object
class pSolution
{
public:
    pSolution();
    pSolution( pSolution * ptr );
    pSolution( pSolution & rhs );
    virtual ~pSolution();

    float penalty( pMap * map ); /// goal function, returns costs
    bool equals( pSolution * s );
    void release();
    void init_random( pMap * map );

    std::vector<pTransmitter*> list;
};

#endif // __P_SOLUTION_H__

