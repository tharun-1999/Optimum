ExecutorService exec = Executors.newFixedThreadPool(4);

CompletableFuture<Data> fa = CompletableFuture.supplyAsync(() -> new TaskA().call(), exec);
CompletableFuture<Data> fb = CompletableFuture.supplyAsync(() -> new TaskB().call(), exec);

CompletableFuture<Data> fc = fa.thenCombineAsync(fb, (a, b) -> new TaskC(a, b).call(), exec);
CompletableFuture<Data> fd = fa.thenCombineAsync(fb, (a, b) -> new TaskD(a, b).call(), exec);

CompletableFuture<Data> ff = fc.thenCombineAsync(fd, (c, d) -> new TaskF(c, d).call(), exec);

ff.whenComplete((result, ex) -> {
  if (ex != null) {
    ex.printStackTrace();
  } else {
    System.out.println("Final result: " + result);
  }
  exec.shutdown();
});
