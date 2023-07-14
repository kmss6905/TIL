n, k = map(int, input().split())
a = list(map(int, input().split()))
b = list(map(int, input().split()))
a.sort() # nlogn
b.sort(reverse=True) # nlogn

for i in range(k):
    if a[i] < b[i]:
        a[i], b[i] = b[i], a[i]
    else: 
        break

print(sum(a))