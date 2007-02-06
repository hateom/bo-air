#include "p_cost_mgr.h"

namespace pc
{

float transmitter_cost( int type )
{
    return( (float)type*1000.f - (float)(type*type*20) );
}

float transmitter_range( int type )
{
    return( type*5.0f );
}

float building_profit( int building )
{
    return( (float)building*10.0f*30.0f );
}

int transmitter_type_count()
{
    return 4;
}

}
