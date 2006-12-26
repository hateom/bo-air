#include <stdio.h>
#include <string.h>
#include "p_parser.h"

#define BUF_SIZE 128

pParser * pParser::singleton()
{
    static pParser static_object;
    return( &static_object );
}

pParser::pParser()
{
}

pParser::~pParser()
{
}

bool pParser::process( const char * in_file )
{
    FILE * file;
    char buffer[BUF_SIZE+1];
    int r_size;

    file = fopen( in_file, "r" );
    if( !file ) 
    {
        return( false );
    }

    while( !feof( file ) )
    {
        fgets( buffer, BUF_SIZE, file );
        //if( buffer[strlen(buffer)-1] == '\n' ) buffer[strlen(buffer)-1] = '\0';
        printf( "%s", buffer );
        buffer[0] = '\0';
    }

    fclose( file );

    return( true );
}

void pParser::release()
{

}
