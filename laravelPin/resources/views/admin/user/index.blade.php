@extends('admin.layouts.layout')

@section('title')

  Users

@endsection


@section('header')

  <!-- DataTables -->
  {!! Html::style('admin/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css')  !!}

@endsection

@section('content')

<section class="content-header">
      <h1>
        Dashboard
        <small>Control panel</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Dashboard</li>
      </ol>
    </section>

<div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner">
              <h3>160</h3>

              <p>Bottles</p>
            </div>
            <div class="icon">
              <i class="ion ion-bag"></i>
            </div>
            <a class="small-box-footer" href="#">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3>10<sup style="font-size: 20px"></sup></h3>

              <p>Recycle Pins</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a class="small-box-footer" href="#">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3>3</h3>

              <p>Users</p>
            </div>
            <div class="icon">
              <i class="ion ion-person-add"></i>
            </div>
            <a class="small-box-footer" href="#">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h3>8</h3>

              <p>Total cash</p>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a class="small-box-footer" href="#">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
      </div>
<!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        User Table
      </h1>
      <ol class="breadcrumb">
        <li><a href="{!! url('/adminpanel') !!}"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="{!! url('/adminpanel/users') !!}">Tables</a></li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">

            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>Phone ID</th>
                  <th>User Name</th>
                  <th>Bottles</th>
                  <th>Wallet</th>
                </tr>
                </thead>
                <tbody>

                <tr>
                  <td>A1NGAR1840900445</td>
                  <td>Mazin Altokais</td>
                  <td>20</td>
                  <td>1.0</td>
                </tr>
                <tr>
                  <td>A1NGAR18407969473</td>
                  <td>Osamah Alrayes</td>
                  <td>40</td>
                  <td>2.0</td>
                </tr>
                  <td>A1NGAR184048484</td>
                  <td>Zeyad Alhusainan</td>
                  <td>100</td>
                  <td>5.0</td>
                </tr>

                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>





@endsection



@section('footer')

  <!-- DataTables -->
  {!! Html::script('admin/bower_components/datatables.net/js/jquery.dataTables.min.js')  !!}
  {!! Html::script('admin/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js')  !!}

<script type="text/javascript">
  $('#example2').DataTable({
        'paging'      : true,
        'lengthChange': true,
        'searching'   : true,
        'ordering'    : true,
        'info'        : true,
        'autoWidth'   : false
  });
  </script>


@endsection
