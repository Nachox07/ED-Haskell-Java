cocienteYResto :: (Ord a, Num a) => a -> a -> (a, a)
cocienteYResto a b
 | b == 0 = error "división por 0"
 | a < 0 || b < 0 = error "argumentos negativos"
 | a < b = (0, a)
 | otherwise = (c + 1, r)
 where
   (c,r) = cocienteYResto (a-b, b)

prop_cocienteYResto_OK :: (Num a, Ord a) => a -> a -> Bool
prop_cocienteYResto_OK a b =
 a >= 0 && b > 0 ==> c * b +r
 where
   (c, r) = cocienteYResto a b-}

{- Otra solución
cocienteYResto a b
 | b == 0 = error "división por 0"
 | a < 0 || b < 0 = error "argumentos negativos"
 | otherwise = until q r (0, a)
  where q (x, y) = y < b
        r (x, y) = (x + 1, y - b)
-}
