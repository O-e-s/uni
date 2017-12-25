// This file is adapted from part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.

load Add8.hdl,
output-file Add8.out,
compare-to Add8.cmp,
output-list carryIn%B3.1.3 a%B1.8.1 b%B1.8.1 out%B1.8.1 carryOut%B3.1.3;

set carryIn  %B0,
set a %B00000000,
set b %B00000000,
eval,
output;

set a %B00000000,
set b %B11111111,
eval,
output;

set a %B11111111,
set b %B11111111,
eval,
output;

set a %B10101010,
set b %B01010101,
eval,
output;

set a %B01101001,
set b %B00111100,
eval,
output;

set a %B01000110,
set b %B01001110,
eval,
output;

set a %B10000000,
set b %B01111111,
eval,
output;

set a %B10000000,
set b %B10000000,
eval,
output;

set carryIn  %B1,
set a %B00000000,
set b %B00000000,
eval,
output;

set a %B00000000,
set b %B11111111,
eval,
output;

set a %B11111111,
set b %B11111111,
eval,
output;

set a %B10101010,
set b %B01010101,
eval,
output;

set a %B01101001,
set b %B00111100,
eval,
output;

set a %B01000110,
set b %B01001110,
eval,
output;

set a %B10000000,
set b %B01111111,
eval,
output;

set a %B10000000,
set b %B10000000,
eval,
output;

