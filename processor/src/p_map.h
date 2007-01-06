#ifndef __P_MAP_H__
#define __P_MAP_H__

#include <vector>

class pMap
{
public:
    pMap() {}
    ~pMap() {}


    std::vector<pBuilding*> buildings;
    std::vector<pTransmitter*> transmitters;
};

#endif // __P_MAP_H__
