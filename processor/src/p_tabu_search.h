#ifndef __P_TABU_SEARCH_H__
#define __P_TABU_SEARCH_H__

#include "p_map.h"
#include "p_solution.h"

class pTabuSearch
{
public:
    pTabuSearch();
    virtual ~pTabuSearch();

    int  exec();
    void free();

    void create_tl( size_t siz );
    int & short_list( size_t i, size_t j );
    int & long_list( size_t i, size_t j );
    void decrease_short_list();

protected:
    int * tabu_list;
    size_t ssize;
};

#endif // __P_TABU_SEARCH_H__

