#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include "p_parser.h"

#define COL_RED "\e[31m"
#define COL_BLU "\e[36m"
#define COL_GRN "\e[32m"
#define COL_DEF "\e[37m"

static int silent = 0;

bool file_exists( const char * filename )
{
    FILE * file;

    file = fopen( filename, "r" );
    if( !file ) return( false );
    fclose( file );
    return( true );
}

void prints( const char * str, ... )
{
    if( silent ) return;
    char buffer[512];
    va_list al;
    va_start( al, str );
    vsprintf( buffer, str, al );
    va_end( al );

    printf( "%s", buffer );
}

int main( int argc, char * argv[] )
{
    int file_in = 1;

    if( argc == 3 && strcmp( argv[1], "-s" ) == 0 )
    {
        silent = 1;
        file_in = 2;
    }

    prints( "%s", COL_DEF );
    if( argc == 1 || argc > 3 )
    {
        prints( "!!! %sERROR%s> usage:\t%s (-s) <input_file>\n\t-s\t silent mode\n\n", COL_RED, COL_DEF, argv[0] );
        return( -1 );
    }

    prints( ">>> using input file <%s>... ", argv[file_in] );
    if( !file_exists( argv[file_in] ) )
    {
        prints( "[ %s!!%s ]\n!!! ERROR> file <%s> does not exists!\n", COL_RED, COL_DEF, argv[file_in] );
        return( -2 );
    }
    else
    {
        prints( "[ %sOK%s ]\n", COL_GRN, COL_DEF );
    }

    prints( ">>> processing input file and generating output.\n" );
    Parser->process( argv[file_in] );
    Parser->release();

    return( 0 );
}

