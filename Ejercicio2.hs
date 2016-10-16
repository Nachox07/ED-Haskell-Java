{-
 - Ejercicio 1
 - Función que multiplique los elementos de una lista
 -}
producto :: (Integral a) => [a] -> a
producto [] = 1
producto (x:xs) = x * producto xs

{-
 - Ejercicio 2
 - Función que saque el elemento máximo de una lista
-}
maximo :: Ord a => [a] -> a
maximo [] = error "maximo de lista vacia"
maximo [x] = x
maximo (x:xs) = max x (maximo xs)

{-
 - Ejercicio 3
 - Función que devuelve en una tupla de logintud dos numeros pares e impares de una lista
 -}
paresImpares :: Integral a => [a] -> ([a],[a])
paresImpares [] = ([],[])
paresImpares (x:xs)
  | even x = (x:pares, impares)
  | otherwise = (pares, x:impares)
  where
    (pares, impares) = paresImpares xs
