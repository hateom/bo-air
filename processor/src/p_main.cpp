#include <stdio.h>
#include "p_parser.h"

#define COL_RED "\e[31m"
#define COL_BLU "\e[36m"
#define COL_GRN "\e[32m"
#define COL_DEF "\e[37m"

bool file_exists( const char * filename )
{
    FILE * file;

    file = fopen( filename, "r" );
    if( !file ) return( false );
    fclose( file );
    return( true );
}

int main( int argc, char * argv[] )
{
    printf( "%s", COL_DEF );
    if( argc == 1 || argc > 2 )
    {
        printf( "!!! %sERROR%s> usage:\t%s <input_file>\n", COL_RED, COL_DEF, argv[0] );
        return( -1 );
    }

    printf( ">>> using input file <%s>... ", argv[1] );
    if( !file_exists( argv[1] ) )
    {
        printf( "[ %s!!%s ]\n!!! ERROR> file <%s> does not exists!\n", COL_RED, COL_DEF, argv[1] );
        return( -2 );
    }
    else
    {
        printf( "[ %sOK%s ]\n", COL_GRN, COL_DEF );
    }

    printf( ">>> processing input file and generating output.\n" );
    Parser->process( argv[1] );
    Parser->release();

    return( 0 );
}

