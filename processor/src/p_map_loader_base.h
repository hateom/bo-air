#ifndef __P_MAP_LOADER_BASE_H__
#define __P_MAP_LOADER_BASE_H__

#include <vector>
#include "p_error.h"

class pMapLoaderBase
{
public:
    pMapLoaderBase() : data(0), width(0), height(0) {}
    virtual ~pMapLoaderBase() {}

    virtual int  read_data( const char * filename ) = 0;
    virtual void free() = 0;

    inline unsigned int get_width() const { return( width ); }
    inline unsigned int get_height() const { return( height ); }
    inline int get_data( unsigned int x, unsigned int y ) const 
    { P_ASSERT( x < width && y < height, "arguments are out of range" ); return( data[x+y*width] ); }

protected:
    std::vector<int> data;
    unsigned int width, height;
};

#endif // __P_MAP_LOADER_BASE_H__

