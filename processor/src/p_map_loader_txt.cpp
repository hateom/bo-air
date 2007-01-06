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

    first_run = true;

    while( !file.eof() )
    {
        std::getline( file, line );
        if( !line.size() ) break;
        pOut->print( "%s [%d]\n", line.c_str(), line.size() );
        for( std::string::iterator it=line.begin(); it!=line.end(); ++it )
        {
            data.push_back( *it );
        }

        if( first_run )
        {
            first_run = false;
            base_width = line.size();
        }
        else
        {
            if( base_width != line.size() )
            {
                file.close();
                pOut->print( "%s!!!%s every line must have the same width!\n", COL_RED, COL_DEF );
                return( -2 );
            }
        }
    }

    file.close();

    return( 0 );
}

void pMapLoaderTxt::free()
{
    data.clear();
}

