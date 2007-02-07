#ifndef __P_COST_MGR_H__
#define __P_COST_MGR_H__

#include <vector>

class pc
{
public:
    static float transmitter_cost( int type );
    static float transmitter_range( int type );
    static float building_profit( int building );
    static int   transmitter_type_count();

    static void  set_default();

    static int   load( const char * filename );
    static int   free();

//private:
    static int   parse_line( char * line );
    
    static std::vector<float> profit;
    static std::vector<float> cost;
    static std::vector<float> range;
};

#endif // __P_COST_MGR_H__

