module MaxiphobicHeapDemos where

import Data.List(nub)

import MaxiphobicHeap
import DataStructures.Util.Random
import DataStructures.Graphics.DrawTrees

{-

Given a String 'xs' builds a series of maxiphobic heaps adding
chars from 'xs' one by one, from head to last. The drawings of
these heaps are stored in .svg files named "Seq-ys.svg", where
'ys' is a prefix of 'xs' with the chars inserted so far.
For example:

  > drawStepwise "blue"

generates the files:

   "Seq-b.svg",
   "Seq-bl.svg",
   "Seq-blu.svg",
   "Seq-blue.svg".

-}

drawStepwise :: String -> IO [()]
drawStepwise xs =
  mapM (\ ys -> drawOnWith ("Seq-" ++ ys ++ ".svg") (:[]) (mkHeap' ys)) [ prefix | i <- [1..length xs], let prefix = take i xs]
   where
     mkHeap' = foldl (flip insert) empty

{-

Given a String 'xs' draws the maxiphobic heap returned by
by 'mkHeap'. The function 'mkHeap' implements an efficient O(n)
bottom-up construction for maxiphobic heaps, similar to the
algorithm we applied to leftists heaps in class. For example:

  > drawPairwise "blue"

generates the file "Par-blue.svg"

-}

drawPairwise :: String -> IO ()
drawPairwise xs = drawOnWith ("Pair-" ++ xs ++ ".svg") (:[]) (mkHeap xs)
