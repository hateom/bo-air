#ifndef __P_UTILS_H__
#define __P_UTILS_H__ 

#include <string>

namespace p_utils
{

bool file_exists( const char * filename );
bool file_exists( std::string filename );

}

#endif // __P_UTILS_H__
