#ifndef __P_COST_MGR_H__
#define __P_COST_MGR_H__

namespace pc
{
    float transmitter_cost( int type );
    float transmitter_range( int type );
    float building_profit( int building );
    int   transmitter_type_count();
};

#endif // __P_COST_MGR_H__

