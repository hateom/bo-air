#ifndef __P_MAP_H__
#define __P_MAP_H__

#include <vector>
#include "p_solution.h"
#include "p_object.h"

#define Map pMap::single()

class pMap
{
public:
    static pMap * single();

    pMap();
    ~pMap();

    int  load( const char * filename );
    void free();

    size_t  solution_size();
    float   eval( pSolution * sol );

protected:
    std::vector<p_object*> building;
    std::vector<p_object*> transmitter;
    int   w, h;
};

#endif // __P_MAP_H__
