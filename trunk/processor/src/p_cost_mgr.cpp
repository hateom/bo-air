#include <fstream>
#include <sstream>
#include "p_error.h"
#include "p_cost_mgr.h"

#define MAX_BUFFER 1024

std::vector<float> pc::profit;
std::vector<float> pc::cost;
std::vector<float> pc::range;

float pc::transmitter_cost( int type )
{
//    static float g_tc[] = { 0.0f, 1000.0f, 2500.0f, 4500.0f, 5000.0f };
//    return( g_tc[type] );
    P_ASSERT( type >= 0 && type < cost.size(), "out of range!" );
    return cost[type];
}

float pc::transmitter_range( int type )
{
//    static float g_tr[] = { 0.0f,    3.0f,    5.0f,    6.0f,    7.0f };
//    return( g_tr[type] );
    P_ASSERT( type >= 0 && type < range.size(), "out of range!" );
    return range[type];
}

float pc::building_profit( int type )
{
//    return( (float)building*10.0f*30.0f );
    P_ASSERT( type >= 0 && type < profit.size(), "out of range!" );
    return profit[type];
}

int pc::transmitter_type_count()
{
    return range.size()-1;
}

void pc::set_default()
{
    free();

    cost.push_back(    0.0f );
    cost.push_back( 1000.0f );
    cost.push_back( 2500.0f );
    cost.push_back( 4500.0f );
    cost.push_back( 5000.0f );

    range.push_back( 0.0f );
    range.push_back( 3.0f );
    range.push_back( 5.0f );
    range.push_back( 6.0f );
    range.push_back( 7.0f );

    for( int i='a'; i<='z'; ++i )
    {
        profit.push_back( (i-'a'+1)*300.0f );
    }
}

int pc::parse_line( char * line )
{
    std::string str( line );
    std::string name;
    std::string rest;
    std::string val;
    size_t i;
    float fval;
    std::vector<float> * list;

    i = str.find( ':' );
    name = str.substr( 0, i );
    rest = str.substr( i+1 );

    if( name == "range" )
    {
        list = &range;
    }
    else if( name == "cost" )
    {
        list = &cost;
    }
    else if( name == "profit" )
    {
        list = &profit;
    }
    else
    {
        return -1;
    }

    while( ( i = rest.find( ';' ) ) != std::string::npos )
    {
        val = rest.substr( 0, i );
        rest.erase( 0, i+1 );
        std::istringstream sval( val );
        if( sval >> fval )
        {
            list->push_back( fval );
        }
        else
        {
            pError( "pCfgMgr: conversion error!" );
        }
    }

    return 0;
}

int pc::load( const char * filename )
{
    free();
    
    std::ifstream ifile;
    static char buffer[MAX_BUFFER] = "";

    ifile.open( filename );
    if( !ifile.is_open() )
    {
        pError( "Cannot read configuration" );
        return -1;
    }

    while( !ifile.eof() )
    {
        ifile.getline( buffer, MAX_BUFFER );
        if( !strcmp(buffer, "") ) continue;
        parse_line( buffer );
    }

    ifile.close();

    if( cost.size() == 0 || range.size() == 0 || profit.size() == 0 )
    {
        pError( "pCostMgr: config error! setting default parameters. (%d,%d,%d)",
            cost.size(), range.size(), profit.size() );
        set_default();
        return -1;
    }

    return 0;
}

int pc::free()
{
    cost.clear();
    range.clear();
    profit.clear();
    return 0;
}

