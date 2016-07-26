package by.cbs.portal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.yahor_babich.rportal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.cbs.portal.ui.AboutActivity;
import by.cbs.portal.ui.NoDefaultSpinner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String[] brandData = {"Acer", "Founder", "Gateway", "Packard Bell", "eMachines"};
    String[] productLineData = {"Acer TV", "Android Tablet", "BYOC", "Desktop", "Monitor", "Netbook", "Others", "Projector", "Server", "Smart Handheld", "Windows Tablet"};
    String[] seriesData = {"Aspire", "Aspire one", "Extensa", "Ferrari", "Iconia", "None", "One", "Predator", "TravelMate"};
    String[] modelNameData = {"AS4315(WISTRON)", "AS3830TG(COMPAL)", "AS3830(COMPAL)", "AS4710(WISTRON)", "AS5030(COMPAL)"};

    @BindView(R.id.brand_spinner)
    NoDefaultSpinner brandSpinner;

    @BindView(R.id.product_line_spinner)
    NoDefaultSpinner productLineSpinner;

    @BindView(R.id.series_spinner)
    NoDefaultSpinner seriesSpinner;

    @BindView(R.id.model_name_spinner)
    NoDefaultSpinner modelNameSpinner;

    @BindView(R.id.download_button)
    Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Acer Services Portal");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fillBrand();
    }

    private void fillBrand() {
        ArrayAdapter<String> brandAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, brandData);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        brandSpinner.setAdapter(brandAdapter);
        brandSpinner.setPrompt("Select...");
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillProductLine();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void fillProductLine() {
        ArrayAdapter<String> productLineAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, productLineData);
        productLineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        productLineSpinner.setAdapter(productLineAdapter);
        productLineSpinner.setPrompt("Select...");
        productLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillSeries();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void fillSeries() {
        ArrayAdapter<String> seriesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, seriesData);
        seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        seriesSpinner.setAdapter(seriesAdapter);
        seriesSpinner.setPrompt("Select...");
        seriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillModelName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void fillModelName() {
        ArrayAdapter<String> modelNameAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, modelNameData);
        modelNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modelNameSpinner.setAdapter(modelNameAdapter);
        modelNameSpinner.setPrompt("Select...");
        modelNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //showIssues();
                downloadButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void copyAssets() {
        File outFile = null;

        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("BUL_REPORT_exp_20160623174350.xls");
            outFile = new File(getExternalFilesDir(null), "BUL_REPORT_exp_20160623174350.xls");
            out = new FileOutputStream(outFile);
            copyFile(in, out);
        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file: " + "BUL_REPORT_exp_20160623174350.xls", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }

        if (outFile.exists() == true) {
            Uri uri = Uri.fromFile(outFile);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No Application Available to View Excel", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @OnClick(R.id.download_button)
    public void submit(View view) {
        copyAssets();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_exit) {
            super.onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
