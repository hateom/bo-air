#include "p_parser.h"

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
    return( true );
}

void pParser::release()
{

}
