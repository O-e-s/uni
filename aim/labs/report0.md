# Part a: Introduction
> What happens if you modify the seed array to contain 10 elements of the same 
> seed?
Instead of just one, ten outputs are provided.  
The first value (`seed`) and second (`f_best`) are the same each time, but the
time taken varies, ranging between 7.7 and 10 on my machine.

> What happens if you now change these seeds, keeping the seed array to be of
> size 10, such that each seed has a different value? 
Ten outputs are provided, in which the seeds are the respective values I
entered.  
The `f_best` values vary between 339 and 371. The time taken also varies,
between 7.7 and 10.7 on my machine.

# Part b: Exercises
> What is the purpose of a seed in experimental design? 
A _seed_ is used in (pseudo-)random number generators. Using the same seed results
in the same sequence of numbers being generated each time.  
In experiments, this is useful in testing something with a deterministic set of
numbers.

> With relation to the resulting solution quality, what happens when the same 
> seed is used over multiple trials? 
Using the same seed produces the same `f_best`, indicating the result is of the
same quality each time.

> With relation to the resulting solution quality, what happens when different
> seeds are used for each trial? 
Runs using different seeds produce different `f_best`, indicating that the
quality of the results vary between seeds.

>In the example, RandomWalk was used for solving the MAX-SAT problem. Using 
> the results obtained from 10 trials using different seeds, you are asked to
> fill out the following results.
>
> mean average solution quality, best solution quality, worst solution quality,
> and mean average runtime (seconds)).
| Result name | Value |
|:-|:-|
| Mean average solution quality | 363.25 |
| Best solution quality | 371 |
| Worst solution quality | 339 |
| Mean average runtime (secs) | 8.655 (4sf) |
