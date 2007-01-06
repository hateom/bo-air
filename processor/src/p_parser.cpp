#include <stdio.h>
#include <string.h>
#include "p_parser.h"
#include "p_map_loader_txt.h"

pParser * pParser::singleton()
{
    static pParser static_object;
    return( &static_object );
}

pParser::pParser() : loader( NULL )
{
}

pParser::~pParser()
{
    release();
}

bool pParser::process( const char * in_file, pMap * map )
{
    if( !map ) return( false );

    loader = new pMapLoaderTxt();
    int item;

    if( loader->read_data( in_file ) != 0 )
    {
        return( false );
    }

    for( unsigned int y=0; y<loader->get_height(); ++y )
    {
        for( unsigned int x=0; x<loader->get_width(); ++x )
        {
            item = loader->get_data( x, y );
            if( item == '+' )
            {
                map->buildings.push_back( new pBuilding( x, y, 1 ) );
            }
            if( item == ' ' )
            {
                map->transmitters.push_back( new pTransmitter( x, y, 0 ) );
            }
        }
    }

    return( true );
}

void pParser::release()
{
    for( size_t i=0; i<buildings.size(); ++i )
    {
        delete buildings[i];
    }
    buildings.clear();

    for( size_t i=0; i<transmitters.size(); ++i )
    {
        delete transmitters[i];
    }
    transmitters.clear();

    if( loader != NULL )
    {
        loader->free();
        delete loader;
        loader = NULL;
    }
}
