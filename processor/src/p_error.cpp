#include "p_error.h"
#include <cstdarg>
#include <iostream>
#include "p_output.h"

std::vector<char *> pErrorMgr::list;

void pErrorMgr::report( const char * err_msg )
{
    list.push_back( strdup( err_msg ) );
}

void pErrorMgr::print()
{
    if( !list.size() ) return;

    pOut->print( ">> error stack:\n" );
    for( size_t i=0; i<list.size(); ++i )
    {
        pOut->print( "!! %s\n", list[i] );
    }
}

pError::pError( const char * err, ... )
{
    va_list al;
    static char buffer[1024] = "";

    va_start( al, err );
    vsprintf( buffer, err, al );
    va_end( al );
   
    pErrorMgr::report( buffer );
}

