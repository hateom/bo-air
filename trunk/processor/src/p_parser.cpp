#include <stdio.h>
#include <string.h>
#include "p_parser.h"
#include "p_map_loader_txt.h"
#include "p_output.h"

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
    release( NULL );
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
            if( item >= 'a' && item <= 'z' )
            {
                map->buildings.push_back( new pBuilding( x, y, 100*(item-'a') ) );
            }
            if( item == ' ' )
            {
                map->transmitters.push_back( new pTransmitter( x, y, 0 ) );
            }
        }
    }

    return( true );
}

void pParser::release( pMap * map )
{
	size_t i;

    if( map )
    {
        for( i=0; i<map->buildings.size(); ++i )
        {
            delete map->buildings[i];
        }
        map->buildings.clear();

        for( i=0; i<map->transmitters.size(); ++i )
        {
            delete map->transmitters[i];
        }
        map->transmitters.clear();
    }

    if( loader != NULL )
    {
        loader->free();
        delete loader;
        loader = NULL;
    }
}
