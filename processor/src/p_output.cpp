#include <cstdarg>
#include <iostream>
#include "p_output.h"
#include "p_cfg_mgr.h"

pOutput::pOutput() : silent(false)
{
}

pOutput::~pOutput()
{
}

void pOutput::print( const char * str, ... )
{
    static char buffer[256] = "";
    va_list al;

    if( silent ) return;

    va_start( al, str );
    vsprintf( buffer, str, al );
    va_end( al );

    std::cout << COL_DEF << buffer << COL_DEF;
}

void pOutput::printa( const char * str, ... )
{
    static char buffer[256] = "";
    va_list al;

    va_start( al, str );
    vsprintf( buffer, str, al );
    va_end( al );

    std::cout << COL_DEF << buffer << COL_DEF;
}

void pOutput::print( std::string str )
{
    print( str.c_str() );
}

void pOutput::printa( std::string str )
{
    printa( str.c_str() );
}

void pOutput::set_silent( bool on )
{
    silent = on;
}

pOutput * pOutput::singleton()
{
    static pOutput static_obj;
    return( &static_obj );
}

const char * pOutput::color( const char * col )
{
#ifdef NO_COLOR
    return "";
#else
    if( silent ) return "";
    return col;
#endif
}

