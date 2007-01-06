#include <stdarg.h>
#include "p_output.h"

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

    printf( "%s%s%s", COL_DEF, buffer, COL_DEF );
}

void pOutput::print( std::string str )
{
    print( str.c_str() );
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

