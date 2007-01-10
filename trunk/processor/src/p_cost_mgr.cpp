#include "p_cost_mgr.h"

namespace pCostMgr
{

static float g_transmitter_cost[P_TRANS_TYPES+1] =  { 0.0f, 100.0f, 180.0f, 245.0f };
static float g_transmitter_range[P_TRANS_TYPES+1] = { 0.0f, 5.0f, 9.0f, 15.0f };

float get_transmitter_cost( int type )
{
    if( type < 0 || type >= P_TRANS_TYPES ) return( -1.0f );
    return( g_transmitter_cost[type] );
}

float get_transmitter_range( int type )
{
    if( type < 0 || type >= P_TRANS_TYPES ) return( -1.0f );
    return( g_transmitter_range[type] );
}

float get_person_profit()
{
    return( 30.0 );
}

}
