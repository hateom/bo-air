#include "p_map_loader_txt.h"
#include "p_output.h"
#include <fstream>
#include <iostream>
#include <string>

pMapLoaderTxt::pMapLoaderTxt()
{
}

pMapLoaderTxt::~pMapLoaderTxt()
{
    free();
}

int pMapLoaderTxt::read_data( const char * filename )
{
    std::ifstream file;
    std::string line;

    file.open( filename, std::ios::in );
    if( !file.is_open() )
    {
        return( -1 );
    }

    height = 0;

    while( !file.eof() )
    {
        std::getline( file, line );
        if( !line.size() ) break;
        pOut->print( "%s [%s%d%s]\n", line.c_str(), COL_BLU, line.size(), COL_DEF );
        for( std::string::iterator it=line.begin(); it!=line.end(); ++it )
        {
            data.push_back( *it );
        }

        if( height == 0 )
        {
            width = line.size();
        }
        else
        {
            if( width != line.size() )
            {
                file.close();
                pOut->print( "%s!!!%s every line must have the same width!\n", COL_RED, COL_DEF );
                return( -2 );
            }
        }
        height++;
    }

    file.close();

    return( 0 );
}

void pMapLoaderTxt::free()
{
    data.clear();
}

