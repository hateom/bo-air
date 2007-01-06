#ifndef __PPARSER_H__
#define __PPARSER_H__

#include <vector>
#include "p_map_loader_base.h"
#include "p_building.h"
#include "p_transmitter.h"
#include "p_map.h"

#define Parser pParser::singleton()

class pParser
{
public:
    static pParser * singleton();

    pParser();
    ~pParser();

    bool process( const char * in_file, pMap * map );
    void release();
    
private:
    pMapLoaderBase * loader;
    std::vector<pBuilding*> buildings;
    std::vector<pTransmitter*> transmitters;
};

#endif // __PPARSER_H__

