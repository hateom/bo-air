#ifndef __P_MAP_H__
#define __P_MAP_H__

#include <vector>
#include "p_solution.h"
#include "p_object.h"

class pMap
{
public:
    pMap();
    ~pMap();

    int  load( const char * filename );
    void free();

    int  solution_size();
    int  eval( pSolution * sol );

protected:
    std::vector<p_object*> building;
    std::vector<p_object*> transmitter;
    int   w, h;
};

#endif // __P_MAP_H__
