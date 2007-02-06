#include "p_object.h"
#include "p_error.h"

namespace po
{

float in_range( p_object * a, p_object * b, float range )
{
    P_ASSERT( a != NULL && b != NULL, "empy arguments" );

    return ( ((a->x - b->x)*(a->x - b->x)) + ((a->y - b->y)*(a->y - b->y)) ) <= range*range;
}

}

