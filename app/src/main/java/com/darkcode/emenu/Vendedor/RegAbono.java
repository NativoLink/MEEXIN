package com.darkcode.emenu.Vendedor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.Cliente.Cliente;
import com.darkcode.emenu.Cliente.ClienteService;
import com.darkcode.emenu.Printer.DeviceListActivity;
import com.darkcode.emenu.Printer.PrinterServer;
import com.darkcode.emenu.Printer.PrinterServerListener;
import com.darkcode.emenu.R;
import com.datecs.api.card.FinancialCard;
import com.datecs.api.printer.Printer;
import com.datecs.api.printer.PrinterInformation;
import com.datecs.api.printer.ProtocolAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.UUID;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 29/2/16.
 */
public class RegAbono extends AppCompatActivity {

    private Printer mPrinter;
    private ProtocolAdapter mProtocolAdapter;
    private PrinterInformation mPrinterInfo;
    private BluetoothSocket mBluetoothSocket;
    private PrinterServer mPrinterServer;
    private Socket mPrinterSocket;
    private String idvendedor;
    private String cliente;
    private String smonto;

    private boolean mRestart;

    private static final String LOG_TAG = "PrinterSample";
    private static final boolean DEBUG = true;

    // Request to get the bluetooth device
    private static final int REQUEST_GET_DEVICE = 0;

    // Request to get the bluetooth device
    private static final int DEFAULT_NETWORK_PORT = 9100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_abono);
        proceso();
    }
    void proceso(){

        String id_cliente = getIntent().getStringExtra("id_cliente");
        final String id_vendedor= getIntent().getStringExtra("id_vendedor");
        idvendedor = id_vendedor;

        final int id_vendedor_n = Integer.valueOf(id_vendedor);
        final int id_cliente_n = Integer.valueOf(id_cliente);
        final String nombre_cliente = getIntent().getStringExtra("nombre");
        cliente = nombre_cliente;
        getSupportActionBar().setTitle("Meexin - Usuario / Registrar Abono");
//        Toast.makeText(getApplicationContext(), nombre_cliente+"  "+id_cliente, Toast.LENGTH_LONG).show();



        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        ClienteService servicio = restadpter.create(ClienteService.class);
        servicio.postSaldo(id_cliente_n, new Callback<Cliente>() {
            @Override
            public void success(Cliente cliente, Response response) {
                int saldo = cliente.getSaldo();
                String parsear = String.valueOf(saldo);
                String idCliente = String.valueOf(id_cliente_n);
                TextView saldoAct = (TextView)findViewById(R.id.saldoActual);
                TextView user_id = (TextView)findViewById(R.id.user_id);
                TextView user_name = (TextView)findViewById(R.id.user_name);

                user_id.setText(idCliente);
                user_name.setText(nombre_cliente);
                saldoAct.setText(parsear);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        final Button btnVincular = (Button)findViewById(R.id.btnVincular);
        btnVincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitForConnection();
            }
        });

        Button btnAbonar = (Button)findViewById(R.id.btnRegAbono);
        btnAbonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Confirmar");
                alertDialog.setMessage("Desea confirmar el Abono? ");
                alertDialog.setNegativeButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                                VendedorService servicio = restadpter.create(VendedorService.class);
                                final EditText etMonto = (EditText)findViewById(R.id.etAbono);
                                int monto = Integer.valueOf(etMonto.getText().toString());
                                smonto  = etMonto.getText().toString();
                                servicio.postRegAbono(id_cliente_n,id_vendedor_n, monto, new Callback<String>() {

                                    @Override
                                    public void success(String s, Response response) {
                                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                        proceso();
                                        etMonto.setText("");
                                        printText();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                );
                alertDialog.setPositiveButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Abono Cancelado", Toast.LENGTH_LONG).show();
                            }
                        }
                );
                alertDialog.show();

            }
        });
    }


//      =====================
//      |   PRINTER CODES   |
//      =====================

    private final ProtocolAdapter.ChannelListener mChannelListener = new ProtocolAdapter.ChannelListener() {
        @Override
        public void onReadEncryptedCard() {
            toast(getString(R.string.msg_read_encrypted_card));
        }

        @Override
        public void onReadCard() {
            readMagstripe();
        }

        @Override
        public void onReadBarcode() {
            readBarcode(0);
        }

        @Override
        public void onPaperReady(boolean state) {
            if (state) {
                toast(getString(R.string.msg_paper_ready));
            } else {
                toast(getString(R.string.msg_no_paper));
            }
        }

        @Override
        public void onOverHeated(boolean state) {
            if (state) {
                toast(getString(R.string.msg_overheated));
            }
        }

        @Override
        public void onLowBattery(boolean state) {
            if (state) {
                toast(getString(R.string.msg_low_battery));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRestart = false;
        closeActiveConnection();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GET_DEVICE) {
            if (resultCode == DeviceListActivity.RESULT_OK) {
                String address = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                //address = "192.168.11.136:9100";
                if (BluetoothAdapter.checkBluetoothAddress(address)) {
                    establishBluetoothConnection(address);
                } else {
                    establishNetworkConnection(address);
                }
            } else if (resultCode == RESULT_CANCELED) {

            } else {
                finish();
            }
        }
    }

    private void toast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dialog(final int iconResId, final String title, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegAbono.this);
                builder.setIcon(iconResId);
                builder.setTitle(title);
                builder.setMessage(msg);

                android.app.AlertDialog dlg = builder.create();
                dlg.show();
            }
        });
    }

    private void error(final String text, boolean resetConnection) {
        if (resetConnection) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            });

            waitForConnection();
        }
    }

    private void doJob(final Runnable job, final int resId) {
        // Start the job from main thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Progress dialog available due job execution
                final ProgressDialog dialog = new ProgressDialog(RegAbono.this);
                dialog.setTitle(getString(R.string.title_please_wait));
                dialog.setMessage(getString(resId));
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            job.run();
                        } finally {
                            dialog.dismiss();
                        }
                    }
                });
                t.start();
            }
        });
    }

    protected void initPrinter(InputStream inputStream, OutputStream outputStream) throws IOException {
        mProtocolAdapter = new ProtocolAdapter(inputStream, outputStream);

        if (mProtocolAdapter.isProtocolEnabled()) {
            final ProtocolAdapter.Channel channel = mProtocolAdapter.getChannel(ProtocolAdapter.CHANNEL_PRINTER);
            channel.setListener(mChannelListener);
            // Create new event pulling thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            channel.pullEvent();
                        } catch (IOException e) {
                            e.printStackTrace();
                            error(e.getMessage(), mRestart);
                            break;
                        }
                    }
                }
            }).start();
            mPrinter = new Printer(channel.getInputStream(), channel.getOutputStream());
        } else {
            mPrinter = new Printer(mProtocolAdapter.getRawInputStream(), mProtocolAdapter.getRawOutputStream());
        }

        mPrinterInfo = mPrinter.getInformation();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ((ImageView)findViewById(R.id.icon)).setImageResource(R.drawable.icon);
//                ((TextView)findViewById(R.id.name)).setText(mPrinterInfo.getName());
            }
        });
    }

    public synchronized void waitForConnection() {
        closeActiveConnection();

        // Show dialog to select a Bluetooth device.
        startActivityForResult(new Intent(this, DeviceListActivity.class), REQUEST_GET_DEVICE);

        // Start server to listen for network connection.
        try {
            mPrinterServer = new PrinterServer(new PrinterServerListener() {
                @Override
                public void onConnect(Socket socket) {
                    if (DEBUG) Log.d(LOG_TAG, "Accept connection from " + socket.getRemoteSocketAddress().toString());

                    // Close Bluetooth selection dialog
                    finishActivity(REQUEST_GET_DEVICE);

                    mPrinterSocket = socket;
                    try {
                        InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();
                        initPrinter(in, out);
                    } catch (IOException e) {
                        e.printStackTrace();
                        error(getString(R.string.msg_failed_to_init) + ". " + e.getMessage(), mRestart);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void establishBluetoothConnection(final String address) {
        closePrinterServer();

        doJob(new Runnable() {
            @Override
            public void run() {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = adapter.getRemoteDevice(address);
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                InputStream in = null;
                OutputStream out = null;

                adapter.cancelDiscovery();

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Connect to " + device.getName());
                    mBluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
                    mBluetoothSocket.connect();
                    in = mBluetoothSocket.getInputStream();
                    out = mBluetoothSocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_connect) + ". " +  e.getMessage(), mRestart);
                    return;
                }

                try {
                    initPrinter(in, out);
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_init) + ". " +  e.getMessage(), mRestart);
                    return;
                }
            }
        }, R.string.msg_connecting);
    }

    private void establishNetworkConnection(final String address) {
        closePrinterServer();

        doJob(new Runnable() {
            @Override
            public void run() {
                Socket s = null;
                try {
                    String[] url = address.split(":");
                    int port = DEFAULT_NETWORK_PORT;

                    try {
                        if (url.length > 1)  {
                            port = Integer.parseInt(url[1]);
                        }
                    } catch (NumberFormatException e) { }

                    s = new Socket(url[0], port);
                    s.setKeepAlive(true);
                    s.setTcpNoDelay(true);
                } catch (UnknownHostException e) {
                    error(getString(R.string.msg_failed_to_connect) + ". " +  e.getMessage(), mRestart);
                    return;
                } catch (IOException e) {
                    error(getString(R.string.msg_failed_to_connect) + ". " +  e.getMessage(), mRestart);
                    return;
                }

                InputStream in = null;
                OutputStream out = null;

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Connect to " + address);
                    mPrinterSocket = s;
                    in = mPrinterSocket.getInputStream();
                    out = mPrinterSocket.getOutputStream();
                } catch (IOException e) {
                    error(getString(R.string.msg_failed_to_connect) + ". " +  e.getMessage(), mRestart);
                    return;
                }

                try {
                    initPrinter(in, out);
                } catch (IOException e) {
                    error(getString(R.string.msg_failed_to_init) + ". " +  e.getMessage(), mRestart);
                    return;
                }
            }
        }, R.string.msg_connecting);
    }

    private synchronized void closeBlutoothConnection() {
        // Close Bluetooth connection
        BluetoothSocket s = mBluetoothSocket;
        mBluetoothSocket = null;
        if (s != null) {
            if (DEBUG) Log.d(LOG_TAG, "Close Blutooth socket");
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void closeNetworkConnection() {
        // Close network connection
        Socket s = mPrinterSocket;
        mPrinterSocket = null;
        if (s != null) {
            if (DEBUG) Log.d(LOG_TAG, "Close Network socket");
            try {
                s.shutdownInput();
                s.shutdownOutput();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void closePrinterServer() {
        closeNetworkConnection();

        // Close network server
        PrinterServer ps = mPrinterServer;
        mPrinterServer = null;
        if (ps != null) {
            if (DEBUG) Log.d(LOG_TAG, "Close Network server");
            try {
                ps.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void closePrinterConnection() {
        if (mPrinter != null) {
            mPrinter.release();
        }

        if (mProtocolAdapter != null) {
            mProtocolAdapter.release();
        }
    }

    private synchronized void closeActiveConnection() {
        closePrinterConnection();
        closeBlutoothConnection();
        closeNetworkConnection();
        closePrinterServer();
    }

    private void printSelfTest() {
        doJob(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DEBUG) Log.d(LOG_TAG, "Print Self Test");
                    mPrinter.printSelfTest();
                    mPrinter.getVoltage();
                    mPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_print_self_test) + ". " + e.getMessage(), mRestart);
                }
            }
        }, R.string.msg_printing_self_test);
    }


    private void printText() {
        doJob(new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();

                Calendar ahoraCal = Calendar.getInstance();
                String year  = String.valueOf(ahoraCal.get(Calendar.YEAR));
                String mes  = String.valueOf(ahoraCal.get(Calendar.MONTH));
                String dia  = String.valueOf(ahoraCal.get(Calendar.DAY_OF_MONTH));
                String hora  = String.valueOf(ahoraCal.get(Calendar.HOUR_OF_DAY));
                String min  = String.valueOf(ahoraCal.get(Calendar.MINUTE));
                String seg  = String.valueOf(ahoraCal.get(Calendar.SECOND));

                String fecha = dia+"/"+mes+"/"+year+" "+hora+":"+min+":"+seg;

                final EditText etMonto = (EditText)findViewById(R.id.etAbono);
//                int monto = Integer.valueOf(etMonto.getText().toString());



                //String fecha = formatoFecha.getCalendar().getTime().toString();

                sb.append("{reset}{center}{w}{b}Meexin");
//                sb.append("{br}");
                sb.append("{br}");
                sb.append("{reset}{center}{i}Telefono : 829-872-1515{center}{br}");
                sb.append("{reset}{center}{i}Fecha  : "+fecha+"{center}{br}");
//                sb.append("{reset}{center}{i}Abono No.: "+idpedido+"{center}{br}");
                sb.append("{reset}{center}{i}ID Vendedor : "+idvendedor+          "{br}");
                sb.append("{reset}{center}{i}Concepto de : Registro de Abono{center}{br}");
                sb.append("{reset}{center}================================{br}");
                sb.append("{reset}Cliente           Abono   {br}");
                int len = cliente.length();
                while(len<20){
                    cliente = cliente+" ";
                    len++;

                }
                int lenP = smonto.length();
                while(lenP<9){
                    smonto = smonto+" ";
                    lenP++;

                }
                sb.append("{reset}"+cliente+""+smonto+""+"{br}");
                sb.append("{reset}{center}================================{br}");
                sb.append("{br}");
                sb.append("{br}");
                sb.append("{reset}{center}{s}Firma:_______________________{br}");
                sb.append("{reset}{center}{s}Gracias!{br}");

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Print Text");
                    mPrinter.reset();
                    mPrinter.printTaggedText(sb.toString());
                    mPrinter.feedPaper(110);
                    mPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_print_text) + ". " + e.getMessage(), mRestart);
                }
            }
        }, R.string.msg_printing_text);
    }

    private void printImage() {
        doJob(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
                final int width = bitmap.getWidth();
                final int height = bitmap.getHeight();
                final int[] argb = new int[width * height];
                bitmap.getPixels(argb, 0, width, 0, 0, width, height);
                bitmap.recycle();

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Print Image");
                    mPrinter.reset();
                    mPrinter.printImage(argb, width, height, Printer.ALIGN_CENTER, true);
                    mPrinter.feedPaper(110);
                    mPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_print_image) + ". " + e.getMessage(), mRestart);
                }
            }
        }, R.string.msg_printing_image);
    }

    private void printPage() {
        doJob(new Runnable() {
            @Override
            public void run() {
                if (mPrinterInfo == null || !mPrinterInfo.isPageModeSupported()) {
                    dialog(R.drawable.page,
                            getString(R.string.title_warning),
                            getString(R.string.msg_unsupport_page_mode));
                    return;
                }

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Print Page");
                    mPrinter.reset();
                    mPrinter.selectPageMode();

                    mPrinter.setPageRegion(0, 0, 160, 320, Printer.PAGE_LEFT);
                    mPrinter.setPageXY(0, 4);
                    mPrinter.printTaggedText("{reset}{center}{b}PARAGRAPH I{br}");
                    mPrinter.drawPageRectangle(0, 0, 160, 32, Printer.FILL_INVERTED);
                    mPrinter.setPageXY(0, 34);
                    mPrinter.printTaggedText("{reset}Text printed from left to right" +
                            ", feed to bottom. Starting point in left top corner of the page.{br}");
                    mPrinter.drawPageFrame(0, 0, 160, 320, Printer.FILL_BLACK, 1);

                    mPrinter.setPageRegion(160, 0, 160, 320, Printer.PAGE_TOP);
                    mPrinter.setPageXY(0, 4);
                    mPrinter.printTaggedText("{reset}{center}{b}PARAGRAPH II{br}");
                    mPrinter.drawPageRectangle(160 - 32, 0, 32, 320, Printer.FILL_INVERTED);
                    mPrinter.setPageXY(0, 34);
                    mPrinter.printTaggedText("{reset}Text printed from top to bottom" +
                            ", feed to left. Starting point in right top corner of the page.{br}");
                    mPrinter.drawPageFrame(0, 0, 160, 320, Printer.FILL_BLACK, 1);

                    mPrinter.setPageRegion(160, 320, 160, 320, Printer.PAGE_RIGHT);
                    mPrinter.setPageXY(0, 4);
                    mPrinter.printTaggedText("{reset}{center}{b}PARAGRAPH III{br}");
                    mPrinter.drawPageRectangle(0, 320 - 32, 160, 32, Printer.FILL_INVERTED);
                    mPrinter.setPageXY(0, 34);
                    mPrinter.printTaggedText("{reset}Text printed from right to left" +
                            ", feed to top. Starting point in right bottom corner of the page.{br}");
                    mPrinter.drawPageFrame(0, 0, 160, 320, Printer.FILL_BLACK, 1);

                    mPrinter.setPageRegion(0, 320, 160, 320, Printer.PAGE_BOTTOM);
                    mPrinter.setPageXY(0, 4);
                    mPrinter.printTaggedText("{reset}{center}{b}PARAGRAPH IV{br}");
                    mPrinter.drawPageRectangle(0, 0, 32, 320, Printer.FILL_INVERTED);
                    mPrinter.setPageXY(0, 34);
                    mPrinter.printTaggedText("{reset}Text printed from bottom to top" +
                            ", feed to right. Starting point in left bottom corner of the page.{br}");
                    mPrinter.drawPageFrame(0, 0, 160, 320, Printer.FILL_BLACK, 1);

                    mPrinter.printPage();
                    mPrinter.selectStandardMode();
                    mPrinter.feedPaper(110);
                    mPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_print_page) + ". " + e.getMessage(), mRestart);
                }
            }
        }, R.string.msg_printing_page);
    }

    private void printBarcode() {
        doJob(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DEBUG) Log.d(LOG_TAG, "Print Barcode");
                    mPrinter.reset();

                    mPrinter.setBarcode(Printer.ALIGN_CENTER, false, 2, Printer.HRI_BELOW, 100);
                    mPrinter.printBarcode(Printer.BARCODE_CODE128AUTO, "123456789012345678901234");
                    mPrinter.feedPaper(38);

                    mPrinter.setBarcode(Printer.ALIGN_CENTER, false, 2, Printer.HRI_BELOW, 100);
                    mPrinter.printBarcode(Printer.BARCODE_EAN13, "123456789012");
                    mPrinter.feedPaper(38);

                    mPrinter.setBarcode(Printer.ALIGN_CENTER, false, 2, Printer.HRI_BOTH, 100);
                    mPrinter.printBarcode(Printer.BARCODE_CODE128, "ABCDEF123456");
                    mPrinter.feedPaper(38);

                    mPrinter.setBarcode(Printer.ALIGN_CENTER, false, 2, Printer.HRI_NONE, 100);
                    mPrinter.printBarcode(Printer.BARCODE_PDF417, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                    mPrinter.feedPaper(38);

                    mPrinter.setBarcode(Printer.ALIGN_CENTER, false, 2, Printer.HRI_NONE, 100);
                    mPrinter.printQRCode(4, 3, "http://www.datecs.bg");
                    mPrinter.feedPaper(38);

                    mPrinter.feedPaper(110);
                    mPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_print_barcode) + ". " + e.getMessage(), mRestart);
                }
            }
        }, R.string.msg_printing_barcode);
    }

    private void readMagstripe() {
        doJob(new Runnable() {
            @Override
            public void run() {
                String[] tracks = null;
                FinancialCard card = null;

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Read Magstripe");
                    if (mPrinterInfo != null && mPrinterInfo.getName().startsWith("CMP-10")) {
                        // The printer CMP-10 can read only two tracks at once.
                        tracks = mPrinter.readCard(true, true, false, 15000);
                    } else {
                        tracks = mPrinter.readCard(true, true, true, 15000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_read_card) + ". " + e.getMessage(), mRestart);
                }

                if (tracks != null) {
                    StringBuffer msg = new StringBuffer();

                    if (tracks[0] == null && tracks[1] == null && tracks[2] == null) {
                        msg.append(getString(R.string.no_card_read));
                    } else {
                        if (tracks[0] != null) {
                            card = new FinancialCard(tracks[0]);
                        } else if (tracks[1] != null) {
                            card = new FinancialCard(tracks[1]);
                        }

                        if (card != null) {
                            msg.append(getString(R.string.card_no) + ": " + card.getNumber());
                            msg.append("\n");
                            msg.append(getString(R.string.holder) + ": " + card.getName());
                            msg.append("\n");
                            msg.append(getString(R.string.exp_date) + ": " + String.format("%02d/%02d",
                                    card.getExpiryMonth(), card.getExpiryYear()));
                            msg.append("\n");
                        }

                        if (tracks[0] != null) {
                            msg.append("\n");
                            msg.append(tracks[0]);

                        }
                        if (tracks[1] != null) {
                            msg.append("\n");
                            msg.append(tracks[1]);
                        }
                        if (tracks[2] != null) {
                            msg.append("\n");
                            msg.append(tracks[2]);
                        }
                    }

                    dialog(R.drawable.card,
                            getString(R.string.card_info),
                            msg.toString());
                }
            }
        }, R.string.msg_reading_magstripe);
    }

    private void readBarcode(final int timeout) {
        doJob(new Runnable() {
            @Override
            public void run() {
                String barcode = null;

                try {
                    if (DEBUG) Log.d(LOG_TAG, "Read Barcode");
                    barcode = mPrinter.readBarcode(timeout);
                } catch (IOException e) {
                    e.printStackTrace();
                    error(getString(R.string.msg_failed_to_read_barcode) + ". " + e.getMessage(), mRestart);
                }

                if (barcode != null) {
                    dialog(R.drawable.readbarcode, getString(R.string.barcode), barcode);
                }
            }
        }, R.string.msg_reading_barcode);
    }


}
