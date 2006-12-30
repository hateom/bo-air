#ifndef __P_COST_MGR_H__
#define __P_COST_MGR_H__

#define P_TRANS_TYPES 3

namespace pCostMgr
{
    float get_transmitter_cost( int type );
    float get_transmitter_range( int type );
};

#endif // __P_COST_MGR_H__

