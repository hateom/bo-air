#ifndef __P_CFG_MGR_H__
#define __P_CFG_MGR_H__

#include <map>
#include <string>

typedef std::map<std::string, std::string> str_map;
typedef std::pair<std::string, std::string> str_pair;

class pCfgMgr
{
public:
    static int add_cfg( const char * cfg );
    static const char * get( const char * option );
    static bool get_int( const char * option, int * result );
    static void free();

private:
    static str_map list;
};

#endif // __P_CFG_MGR_H__

