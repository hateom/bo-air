#ifndef __P_BUILDING_H__
#define __P_BUILDING_H__

class pBuilding
{
public:
    pBuilding();
    pBuilding( int x, int y, int population );
    ~pBuilding();

    int get_x() const;
    int get_y() const;
    int get_population() const;

    void set_population( int pop );
    void set_pos( int x, int y );

    float profit();

protected:
    int x, y, population;
};

#endif // __P_BUILDING_H__

