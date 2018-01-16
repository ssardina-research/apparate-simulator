## Map Extensions

### Extension to Handle Cost

The system uses maps in the form of the [Moving-AI gridworld domains](http://movingai.com/benchmarks/)

The format of those files can be found here: 

http://movingai.com/benchmarks/formats.html

The standard maps do not include cost of cells and it relies on different type of navigations (by foot, sailing, flying, etc).  In contrast, **APPARATE** is based soley on cost. So, to accommodate that, one can modify the first part of the map to include costs for each cell type.

The cost specification has to come before `map` keyword and respect the following order:

```
ground <cost>
tree <cost>
swamp <cost>
water <cost>
```

where `<cost>` is a `float` or `+inf` (for infinite cost). Infinite cost cells are then assumed to be non-traversable. For example:

```
type octile
height 512
width 512
ground 1
tree +inf
swamp 10
water 20
map
```

Observe the order is important: ground, tree, swamp, water just before the `map` keyword. If an entity is skipped, a default cost is applied.


### Map Scripting for Dynamic Changes

A map scripting file can be given to specify changes in the domain dynamically at run-time. This is useful to test and see how different incremental path planners deal with changes in the environment.

* File name needs to end with `.mapscript` extension.	
* File contains commands, one per line.
* Each command is of the form **`Triggering+ Action`**
    * Thus, a Command is executed only if at least one trigger is valid during the step.
    * Use '#' at the start of the command to mark it as a comment.
* Triggering could be of two types:
    * **Step Triggering** include:
        * `STEP <START> [<LENGTH>:1]`: which steps to execute the action, LENGTH of 0 indicate it is valid for infinite.
        * `REPEAT <INACTIVELEN> [<ACTIVELEN>:1] [<OFFSET>:0] [<START>:0] [<LENGTH>:0]`: repeating steps to execute the action, `LENGTH` of 0 indicate it is valid for infinite. The repeat begins by waiting over the `INACTIVELEN` before executing the number of steps for `ACTIVELEN`.
    * **Conditon Triggering** include:
        * `STARTENTER <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start enters the area specified
        * `STARTLEAVE <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start leaves the area specified
        * `STARTON <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start is within the area specified
        * `STARTOFF <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start is not within the area specified
        * `GOALENTER <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal enters the area specified
        * `GOALLEAVE <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal leaves the area specified
        * `GOALON <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal is within the area specified
        * `GOALOFF <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal is not within the area specified
* The possible actions (to be done when the triggering applies) are:
    * `FILLOUTOFBOUND <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "out of bound" terrain
    * `SETMAXTIME <N>`: set max limit time to `N` milliseconds
    * `FILLWATER <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "water" terrain
    * `FILLGROUND <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "ground" terrain
    * `FILLSWAMP <X> <Y> [<WIDTH>:1 <HEIGHT>:1]` : fill the given area with "swamp" terrain
    * `FILLTREE <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "tree" terrain
    * `PUTSTART <X> <Y>`: place the start at the new given location
    * `PUTGOAL <X> <Y>`: place the goal at the new given location
    * `PUSHSTART <OFFSETX> <OFFSETY>`: move the start by the given offset amount
    * `PUSHGOAL <OFFSETX> <OFFSETY>`: move the goal by the given offset amount

Some examples:

* On the seventh step, fill from <10, 10> to <100, 100> with trees: `STEP 7 FILLTREE 10 10 91 91`
* When the start is within 5 step of the goal at <10, 10>, move the goal to <100, 100>: `STARTENTER 5 5 11 11 PUTGOAL 100 100`
* After the 100 step, move the goal to the right at the rate of 1 move per 7 steps: `REPEAT 6 1 -6 100 0 PUSHGOAL 1 0`
* Every 4 moves, move the start up one step and move the goal left one step: `REPEAT 3 1 PUSHSTART 0 1 PUSHGOAL -1 0`
* When the start is within 4 step of the goal at <10, 10> within 100 steps, move the goal to <20, 20>, else move it to <30, 30>: 

    ```
    STEP 1 100 STARTENTER 6 6 9 9 PUTGOAL 20 20
    STEP 100 0 STARTENTER 6 6 9 9 PUTGOAL 30 30 
    ```