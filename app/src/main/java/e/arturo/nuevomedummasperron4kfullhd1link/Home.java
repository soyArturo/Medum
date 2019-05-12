package e.arturo.nuevomedummasperron4kfullhd1link;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import e.arturo.nuevomedummasperron4kfullhd1link.Fragments.CartFragment;
import e.arturo.nuevomedummasperron4kfullhd1link.Fragments.FavoritesFragment;
import e.arturo.nuevomedummasperron4kfullhd1link.Fragments.HomeFragment;
import e.arturo.nuevomedummasperron4kfullhd1link.Fragments.ProfileFragment;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment home = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,home)
                            .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                            .addToBackStack(null).commit();
                    return true;
                case R.id.navigation_fav:
                    FavoritesFragment fav = new FavoritesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fav)
                            .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                            .addToBackStack(null).commit();
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profile = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profile)
                            .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                            .addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
