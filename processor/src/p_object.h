#ifndef __P_OBJECT_H__
#define __P_OBJECT_H__

struct p_object
{
    p_object( unsigned int nx, unsigned int ny, int ndata ) : x(nx), y(ny), data(ndata) {}
    p_object( unsigned int nx, unsigned int ny ) : x(nx), y(ny) {}
    unsigned int x, y;
    int data;
};

namespace po
{
    float in_range( p_object * a, p_object * b, float range );
};

#endif // __P_OBJECT_H__

