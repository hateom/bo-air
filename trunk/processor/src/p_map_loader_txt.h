#ifndef __P_MAP_LOADER_TXT_H__
#define __P_MAP_LOADER_TXT_H__

#include "p_map_loader_base.h"

class pMapLoaderTxt: public pMapLoaderBase
{
public:
    pMapLoaderTxt();
    virtual ~pMapLoaderTxt();

    virtual  int read_data( const char * filename );
    virtual void free();

protected:
    unsigned int  base_width;
    bool first_run;
};

#endif // __P_MAP_LOADER_TXT_H__

