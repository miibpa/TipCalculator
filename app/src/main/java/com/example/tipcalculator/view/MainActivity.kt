package com.example.tipcalculator.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.example.tipcalculator.R
import com.example.tipcalculator.databinding.ActivityTipCalculatorBinding
import com.example.tipcalculator.viewmodel.CalculatorViewModel

import kotlinx.android.synthetic.main.activity_tip_calculator.*

class MainActivity : AppCompatActivity(), SaveDialogFrafment.Callback, LoadDialogFragment.Callback {

    override fun onTipSelected(name: String) {
        binding.vm?.loadTipCalculation(name)
        Snackbar.make(binding.root, "Loaded $name", Snackbar.LENGTH_SHORT).show()
    }


    override fun onSaveTip(name: String) {
        binding.vm?.saveCurrentTip(name)
        Snackbar.make(binding.root,"Saved $name",Snackbar.LENGTH_SHORT).show()
    }

    lateinit var binding: ActivityTipCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tip_calculator)

        val factory = CalculatorViewModel.Factory(application)
        binding.vm = ViewModelProviders.of(this,factory).get(CalculatorViewModel::class.java)
        binding.setLifecycleOwner(this)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tip_calculator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                true
            }
            R.id.action_load -> {
                showLoadDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSaveDialog() {
        val saveFragment = SaveDialogFrafment()
        saveFragment.show(supportFragmentManager,"SaveDialog")
    }

    private fun showLoadDialog() {
        val loadFragment = LoadDialogFragment()
        loadFragment.show(supportFragmentManager,"LoadDialog")
    }
}
