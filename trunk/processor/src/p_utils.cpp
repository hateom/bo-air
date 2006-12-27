#include "p_utils.h"

namespace p_utils 
{

bool file_exists( const char * filename )
{
    FILE * file;

    file = fopen( filename, "r" );
    if( !file ) return( false );
    fclose( file );
    return( true );
}

bool file_exists( std::string str )
{
    return( file_exists( str.c_str() ) );
}

}
