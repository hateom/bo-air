#include "p_map.h"
#include <fstream>
#include "p_output.h"
#include "p_error.h"
#include "p_cost_mgr.h"

///////////////////////////////////////////////////////////////////////////////////////////

namespace pg 
{
    static pMap g_p_map;
}

pMap * pMap::single()
{
    return( &pg::g_p_map );
}

//////////////////////////////////////////////////////////////////////////////////////////

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
    w = 0;

    ifile.open( filename );
    if( !ifile.is_open() ) return( -1 );

    while( !ifile.eof() )
    {
        ch = ifile.get();
        ++x;
        if( ch == '\n' )
        {
            ++y; if( (int)x > w ) w = x; x = 0;
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
    }

    h = y;

    ifile.close();

    pOut->print( ">> loaded %d transmitters and %d buildings.\n>> Map size is %dx%d.\n",
    transmitter.size(), building.size(), w, h );

    return 0;
}

void pMap::free()
{
    size_t i;

    for( i=0; i<transmitter.size(); ++i )
    {
        delete transmitter[i];
    }
    transmitter.clear();

    for( i=0; i<building.size(); ++i )
    {
        delete building[i];
    }
    building.clear();

    w = h = 0;
}

float pMap::eval( pSolution * sol )
{
    P_ASSERT( sol != NULL, "empty arguments" );

    float result = 0.0f;

    for( size_t i=0; i<sol->size; ++i )
    {
        if( sol->vec[i] == 0 ) continue;
        result += pc::transmitter_cost( sol->vec[i] );
    }

    for( size_t j=0; j<building.size(); ++j )
    {
        for( size_t i=0; i<sol->size; ++i )
        {
            if( sol->vec[i] == 0 ) continue;
            if( po::in_range( transmitter[i], building[j], pc::transmitter_range( sol->vec[i] ) ) )
            {
                result -= pc::building_profit( building[j]->data );
                break;
            }
        }
    }

    return result;
}

size_t pMap::solution_size()
{
    return transmitter.size();
}

size_t pMap::building_count()
{
    return building.size();
}

