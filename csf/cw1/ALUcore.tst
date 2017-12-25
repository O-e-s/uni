// This file is adapted from part of www.nand2tetris.org
// and the book "The Elements of Computag Systems"
// by Nisan and Schocken, MIT Press.

load ALUcore.hdl,
output-file ALUcore.out,
compare-to ALUcore.cmp,
output-list a%B1.8.1 b%B1.8.1 carryIn%B3.1.3 sums%B3.1.3 ands%B3.1.3 xors%B3.1.3 ors%B3.1.3 srs%B3.1.3 nDb%B3.1.3 zeroA%B3.1.3 out%B1.8.1 carryOut%B3.1.3;


// Initial test 

set a %B00000000,
set b %B00000000,
set carryIn  %B0,
set sums     %B0,
set ands     %B0,
set xors     %B0,
set srs      %B0,
set nDb      %B0,
set zeroA    %B0,
eval,
output;

set zeroA    %B1,
eval,
output;

set zeroA    %B0,
set nDb      %B1,
eval,
output;

// Test shift
set nDb      %B1,
set srs      %B1,
set a %B00000000,
eval,
output;

set a %B11111111,
eval,
output;

set a %B10000000,
eval,
output;

set a %B01000000,
eval,
output;

set a %B00100000,
eval,
output;

set a %B00010000,
eval,
output;

set a %B00001000,
eval,
output;

set a %B00000100,
eval,
output;

set a %B00000010,
eval,
output;

set a %B00000001,
eval,
output;

set a %B11000000,
eval,
output;

set a %B01100000,
eval,
output;

set a %B00110000,
eval,
output;

set a %B00011000,
eval,
output;

set a %B00001100,
eval,
output;

set a %B00000110,
eval,
output;

set a %B00000011,
eval,
output;

set a %B10000001,
eval,
output;

// Test Add

set nDb      %B0,
set srs      %B0,
set sums     %B1,
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

// Same but subtract

set nDb      %B1,
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

// Test and


set nDb      %B0,
set carryIn  %B0,
set sums     %B0,
set ands     %B1,
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

set nDb      %B1,
set a %B01000110,
set b %B01001110,
eval,
output;

set nDb      %B0,
set carryIn  %B0,
set sums     %B0,
set ands     %B0,
set xors     %B1,
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

set nDb      %B1,
set a %B01000110,
set b %B01001110,
eval,
output;

set nDb      %B0,
set carryIn  %B0,
set sums     %B0,
set xors     %B0,
set ors      %B1,
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

set nDb      %B1,
set a %B01000110,
set b %B01001110,
eval,
output;