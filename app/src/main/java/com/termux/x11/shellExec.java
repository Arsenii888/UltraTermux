public class shellExec {
    public void runShell(final String command) {

        // Чтобы не вис интерфейс, запускаем в другом потоке
        new Thread(new Runnable() {
            public void run() {
                OutputStream out = null;
                InputStream in = null;
                try {
                    // Отправляем скрипт в рантайм процесс
                    Process child = Runtime.getRuntime().exec("sh");
                    DataOutputStream stdin = new DataOutputStream(child.getOutputStream());

                    stdin.writeBytes(command+"\n");
                    stdin.flush();
                    stdin.writeBytes("exit\n");
                    stdin.flush();
                    BufferedReader stdout = new BufferedReader(new InputStreamReader(child.getInputStream()));
                    BufferedReader stderr=new BufferedReader(new InputStreamReader(child.getErrorStream()));
                    while(true){
                        String s=stderr.readLine();
                        if(s==null){
                            break;
                        }
                        Log.v("err",s);
                    }
                    while(true){
                        String s=stdout.readLine();
                        if(s==null){
                            break;
                        }
                        Log.v("Script out",s);
                    }
                    child.destroy();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
