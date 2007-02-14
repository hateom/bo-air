#include "p_cfg_mgr.h"
#include <stdio.h>
#include <string.h>
#include <sstream>
#include "p_output.h"
#include "p_error.h"

str_map pCfgMgr::list;

int pCfgMgr::add_cfg( const char * cfg )
{
    char * ptr = strdup(cfg);
    char * value;

    if( ptr[0] != '-' || ptr[1] != '-' ) return -1;
    
    ptr += 2;

    value = strstr( ptr, "=" );
    if( value != NULL )
    {
        value++;
        ptr[ value - ptr -1 ] = '\0';
    }
    else
    {
        value = "1";
    }

    list.insert( str_pair( std::string(ptr), std::string(value) ) );
    ::free( ptr-2 );

    return 0;
}

const char * pCfgMgr::get( const char * str )
{
    static char buffer[1024] = "";
    std::string out = list[std::string(str)];
    strcpy( buffer, out.c_str() );
    return buffer;
}

void pCfgMgr::free()
{
    list.clear();
}

bool pCfgMgr::get_int( const char * str, int * result )
{
    P_ASSERT( str != NULL && result != NULL, "empty arguments" );

    std::string out = list[std::string(str)];
    std::istringstream in( out ); 
    int rc; 

    if( out == "" ) return false;

    if( in >> rc )
    {
        *result= rc;
    }
    else
    {
        return false;
    }

    return true;
}

