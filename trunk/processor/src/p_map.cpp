#include "p_map.h"
#include <fstream>

pMap::pMap()
{
}

pMap::~pMap()
{
    free();
}

int pMap::load( const char * filename )
{
    std::ifstream ifile;
    int ch;
    unsigned int x = 0, y = 0;

    ifile.open( filename );
    if( !ifile.is_open() ) return( -1 );

    while( !ifile.oef() )
    {
        ch = ifile.get();
        if( ch == '\n' )
        {
            ++y;
            x = 0;
            continue;
        }
        else if( ch == '0' )
        {
            continue;
        }
        else if( ch >= 'a' && ch <= 'z' )
        {
            building.push_back( new p_object( x, y, ch-'a' ) );
        }
        else if( ch == ' ' )
        {
            transmitter.push_back( new p_object( x, y ) );
        }
        ++x;
    }

    ifile.close();

    return 0;
}

void pMap::free()
{
}

int pMap::eval( pSolution * sol )
{
    return 0;
}

int pMap::solution_size()
{
    return 0;
}

